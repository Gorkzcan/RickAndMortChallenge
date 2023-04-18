package com.gorkem.rmainviousg.repo

import com.gorkem.rmainviousg.model.CharDetailModel
import com.gorkem.rmainviousg.model.LocationById
import com.gorkem.rmainviousg.model.LocationsModel
import com.gorkem.rmainviousg.model.Resource
import com.gorkem.rmainviousg.service.RmaService
import retrofit2.HttpException
import javax.inject.Inject

class ApiRepo @Inject constructor(
    private val rmaService: RmaService
) {

    suspend fun getLocations(pageNumber: Int): Resource<LocationsModel> {
        return try {
            val response = rmaService.getLocations(pageNumber)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Failure(HttpException(response))
            }
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    suspend fun getLocationById(locationId: Int): Resource<LocationById> {
        return try {
            val response = rmaService.getALocationById(locationId)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Failure(HttpException(response))
            }
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }


    suspend fun getMultipleCharacters(characterIds: ArrayList<Int>): Resource<ArrayList<CharDetailModel>> {
        return try {
            val response = rmaService.getMultipleCharacter(characterIds)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Failure(HttpException(response))
            }
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}



/*
* ApiRepo sınıfı ApiService sınıfına bağımlıdır.
* Bu sınıf, 3 farklı işlevi içerir.
* İlk olarak, sayfa numarasına göre tüm lokasyonları almak için getLocations() işlevini kullanır.
* İkinci olarak, belirli bir lokasyonu almak için getLocationById() işlevini kullanır.
* Son olarak, birden fazla karakteri almak için getMultipleCharacters() işlevini kullanır.
* Tüm bu işlevler, API isteklerinin sonuçlarını bir Resource objesi olarak döndürür.
* Bu obje, başarılı veya başarısız bir sonucu gösterir ve ilgili verileri içerir veya içermez.
*/