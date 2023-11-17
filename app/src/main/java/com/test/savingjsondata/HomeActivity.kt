package com.test.savingjsondata

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.test.savingjsondata.adapter.LocalDataAdapter
import com.test.savingjsondata.database.LocalEntity
import com.test.savingjsondata.database.RoomDB
import com.test.savingjsondata.databinding.ActivityHomeBinding
import com.test.savingjsondata.modelfactory.LocalDataModelFactory
import com.test.savingjsondata.modelfactory.PhotoModelFactory
import com.test.savingjsondata.pojos.Photos
import com.test.savingjsondata.repository.LocalDataRepository
import com.test.savingjsondata.repository.PhotosRepository
import com.test.savingjsondata.retrofit.RetrofitClient
import com.test.savingjsondata.utils.Status
import com.test.savingjsondata.viewmodel.LocalDataViewModel
import com.test.savingjsondata.viewmodel.PhotosViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var activityHomeBinding: ActivityHomeBinding
    private lateinit var photoViewModel: PhotosViewModel
    private lateinit var localDataViewModel: LocalDataViewModel
    private lateinit var adapter: LocalDataAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        setupViewModel()
        auth = FirebaseAuth.getInstance()
        localDataViewModel.fetchLocalData()

        localDataViewModel.localDataLiveData.observe(this) {
            if (it.data?.size == 0) {
                photoViewModel.getPhotos()
            } else {
                localDataViewModel.localDataList.clear()
                localDataViewModel.localDataList.addAll(it.data!!)

                adapter.notifyDataSetChanged()
            }
        }

        photoViewModel.photoLiveData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    showProgressBar()
                }

                Status.FAILURE -> {
                    hideProgressBar()
                    Toast.makeText(
                        this,
                        getString(R.string.something_went_wrong),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                Status.SUCCESS -> {
                    val data = it.data
                    storeDataInLocalDB(data)
                }
            }
        }

        adapter = LocalDataAdapter(this, localDataViewModel.localDataList)
        activityHomeBinding.homeRv.adapter = adapter

        activityHomeBinding.homeLogoutIv.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, getString(R.string.logout_text), Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupViewModel() {
        photoViewModel = ViewModelProvider(
            this,
            PhotoModelFactory(PhotosRepository(RetrofitClient.apiInterface))
        )[PhotosViewModel::class.java]

        localDataViewModel = ViewModelProvider(
            this,
            LocalDataModelFactory(LocalDataRepository(RoomDB.getDatabase(this).getDao()))
        )[LocalDataViewModel::class.java]
    }

    private fun storeDataInLocalDB(data: List<Photos>?) {
        for (i in data!!) {
            localDataViewModel.insertLocalData(
                LocalEntity(
                    id = i.id,
                    title = i.title,
                    url = i.url,
                    thumbUrl = i.thumbnailUrl
                )
            )
        }

        adapter.notifyDataSetChanged()
    }

    private fun showProgressBar() {
        activityHomeBinding.homePb.visibility = VISIBLE
    }

    private fun hideProgressBar() {
        activityHomeBinding.homePb.visibility = GONE
    }
}