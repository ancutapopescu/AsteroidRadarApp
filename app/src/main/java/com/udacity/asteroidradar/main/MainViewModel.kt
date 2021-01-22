package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.network.Asteroid
import com.udacity.asteroidradar.network.NasaApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response status
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the status String
    val status: LiveData<String>
    get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of Asteroids
    // with new values.
    private val _asteroidsList = MutableLiveData<List<Asteroid>>()

    // The external LiveData interface to the asteroid is immutable, so only this class can modify.
    val asteroidsList: LiveData<List<Asteroid>>
    get() = _asteroidsList

    // Add an encapsulated LiveData variable for navigating to the selectedAsteroid detail screen.
    private val _navigatetoSelectedAsteroid = MutableLiveData<List<Asteroid>>()
    val navigateToSelectedAsteroid: LiveData<List<Asteroid>>
    get() = _navigatetoSelectedAsteroid

    // Call getAsteroidsList() on init so we can display status immediately.
    init {
        getAsteroidsList()
    }


    // Gets Asteroids information from the Nasa API Retrofit service and updates
    // the Asteroid List LiveData.
    // The Retrofit service returns a coroutine Deferred, which we await to get the result
    // of the transaction.
    private fun getAsteroidsList() {
        viewModelScope.launch {
            try {
                var listResult = NasaApi.retrofitService.getAsteroidsList()
                _status.value = "Success: ${listResult.size}"
                if(listResult.size > 0) {
                    _asteroidsList.value = listResult
                }
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    //Add this function to set _navigateToSelectedAsteroid to asteroid and initiate navigation to the detail screen on button click:
    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigatetoSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidDetailsComplete() {
        _navigatetoSelectedAsteroid.value = null
    }

}