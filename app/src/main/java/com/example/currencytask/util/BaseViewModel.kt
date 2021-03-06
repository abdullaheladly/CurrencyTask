package com.example.currencytask.util


import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.lang.Exception

open class BaseViewModel(application: Application) : AndroidViewModel(application) {


    fun <T> handleFlowResponse(requst:suspend () -> Response<T>)=
        try {
            flow<ApiResult<T>> {
                if (hasInternetConnection()) {
                    emit(ApiResult.Loading(null, true))
                    val response = withContext(Dispatchers.IO) {
                         requst()

                    }


                    if (response.isSuccessful) {
                        emit(ApiResult.Success(response.body()))
                    } else {
                        /* val errorMsg=response.errorBody()?.toString()
             response.errorBody()?.close()
             emit(ApiResult.Error(errorMsg!!))*/
                        if (response.message().toString().contains("timeout")) {
                            emit(ApiResult.Error("Timeout"))
                        } else if (response.code() == 401) {
                            emit(ApiResult.Error("UnAuthenticated"))
                        } else if (response.code() < 500) {

                            val responseObject =
                                JSONObject(response.errorBody()?.string().toString())

                            if (responseObject.has("errors")) {
                                val error = responseObject.get("errors")
                                if (error is String) {
                                    emit(ApiResult.Error(error))
                                }
                                if (error is JSONObject) {
                                    for (key: String in error.keys().iterator()) {
                                        emit(ApiResult.Error(error.getString(key)))
                                    }

                                }
                            }
                            emit(ApiResult.Error(responseObject.getString("message")))


                            // emit(ApiResult.Error(response.errorBody()?.string().toString()))
                            response.errorBody()?.close()
                        } else {
                            emit(ApiResult.Error("error"))

                        }
                    }
                } else {
                    emit(ApiResult.Error("no internet"))

                }
            }.map {
                it
            }.asLiveData()
        }catch(ex: Exception){
            flow<ApiResult<T>> {
                emit((ApiResult.Error("no internet")))
            }.asLiveData()
        }



    private fun hasInternetConnection():Boolean{
        val connectivityManager=getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork=connectivityManager.activeNetwork?:return false
        val capabilities=connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return  when{
            //  connectivityManager.activeNetworkInfo?.isConnected == true -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}