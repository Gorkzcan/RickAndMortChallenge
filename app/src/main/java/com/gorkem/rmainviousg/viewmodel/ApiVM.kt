package com.gorkem.rmainviousg.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorkem.rmainviousg.model.CharDetailModel
import com.gorkem.rmainviousg.model.LocationById
import com.gorkem.rmainviousg.model.LocationsModel
import com.gorkem.rmainviousg.model.Resource
import com.gorkem.rmainviousg.repo.ApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiVM @Inject constructor(
    private val apiRepo:ApiRepo
):ViewModel() {
    //Yazılırken ChatGPT-3 yardımı alınmıştır.

    private val _locationsLiveData = MutableLiveData<Resource<LocationsModel>>()
    val locationsLiveData: LiveData<Resource<LocationsModel>> = _locationsLiveData

    private val _locationByIdLiveData = MutableLiveData<Resource<LocationById>>()
    val locationByIdLiveData: LiveData<Resource<LocationById>> = _locationByIdLiveData

    private val _multipleCharsLiveData = MutableLiveData<Resource<ArrayList<CharDetailModel>>>()
    val multipleCharsLiveData: LiveData<Resource<ArrayList<CharDetailModel>>> = _multipleCharsLiveData

    suspend fun getLocations(pageNumber: Int){
        viewModelScope.launch {
            _locationsLiveData.value = apiRepo.getLocations(pageNumber)
        }
    }

    suspend fun getLocationById(locationId: Int){
        viewModelScope.launch{
            _locationByIdLiveData.value = apiRepo.getLocationById(locationId)
        }
    }

    suspend fun getMultipleChars(charsId: ArrayList<Int>){
        viewModelScope.launch {
            _multipleCharsLiveData.value = apiRepo.getMultipleCharacters(charsId)
        }
    }


}

/*_locationsLiveData gibi öneki alt çizgiyle başlayan değişken isimleri,
Kotlin dilinde genellikle "private mutable live data" (özel değişken veri akışı) değişkenleri için kullanılan bir konvensiyondur.
Bu değişkenler,
ViewModel sınıfı tarafından yönetilen verileri saklamak için kullanılır ve bunların dışarıdan doğrudan erişilememesi sağlanır.
Bu şekilde, bu verilerin sadece ViewModel sınıfı tarafından güncellenmesi ve dışarıya sadece gözlemlenebilmesi sağlanır.


Ayrıca, locationsLiveData gibi öneksiz değişken isimleri, genellikle bu "private mutable live data" değişkenlerinden türetilir ve dışarıya önceden belirlenmiş bir arayüzle sunulur.
Bu arayüz, genellikle bir LiveData örneğidir ve observe() işlevi aracılığıyla gözlemlenebilir.
Bu yöntem, ViewModel sınıfının değişen verileri içerdiğinden ve View katmanının bu verileri gözlemlemesi gerektiğinden, Veri Bağlama Kütüphanesi veya diğer veri gösterim araçları ile kullanışlıdır.


Bu şekilde, ViewModel sınıfının mantığı ve dışarıya sunulan veriler net bir şekilde ayrılmış olur ve bu kodun daha okunaklı ve bakımı daha kolay hale gelir.
 */