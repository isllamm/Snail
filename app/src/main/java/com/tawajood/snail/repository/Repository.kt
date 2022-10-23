package com.tawajood.snail.repository


import com.tawajood.snail.api.RetrofitApi
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.pojo.*
import com.tawajood.snail.utils.toMap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import javax.inject.Inject

class Repository
@Inject
constructor(private val api: RetrofitApi) {

    suspend fun getIdentifiers() =
        api.getIdentifiers(PrefsHelper.getToken(), PrefsHelper.getUserId())

    suspend fun register(registerBody: RegisterBody) =
        api.register(PrefsHelper.getLanguage(), registerBody)

    suspend fun login(countryCode: String, phone: String, password: String) =
        api.login(PrefsHelper.getLanguage(), countryCode, phone, password)

    suspend fun checkPhone(countryCode: String, phone: String) =
        api.checkPhone(PrefsHelper.getLanguage(), countryCode, phone)

    suspend fun getSlider() = api.getSlider(PrefsHelper.getLanguage())

    suspend fun getClinics() = api.getClinics(PrefsHelper.getLanguage())

    suspend fun getProfile() = api.getProfile(PrefsHelper.getToken(), PrefsHelper.getLanguage())
    suspend fun getTerms() = api.getTerms(PrefsHelper.getLanguage())
    suspend fun getAbout() = api.getAbout(PrefsHelper.getLanguage())
    suspend fun getContact() = api.getContact(PrefsHelper.getLanguage())
    suspend fun getConsultants() = api.getMyConsultants(
        PrefsHelper.getToken(),
        PrefsHelper.getLanguage(),
        PrefsHelper.getUserId().toString()
    )

    suspend fun updateProfile(updateBody: ProfileBody): Response<MainResponse<Any>> {
        if (updateBody.image != null) {
            val imagePart = MultipartBody.Part.createFormData(
                "image",
                updateBody.image.name,
                updateBody.image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
            return api.updateProfile(
                PrefsHelper.getToken(),
                PrefsHelper.getLanguage(),
                updateBody.toMap(),
                imagePart
            )
        } else {
            return api.updateProfile(
                PrefsHelper.getToken(),
                PrefsHelper.getLanguage(),
                updateBody
            )
        }
    }

    suspend fun changePassword(newPassword: String) =
        api.changePassword(
            PrefsHelper.getLanguage(),
            PrefsHelper.getPhone(),
            PrefsHelper.getCountryCode(),
            newPassword
        )

    suspend fun changePassword(countryCode: String, phone: String, newPassword: String) =
        api.changePassword(
            PrefsHelper.getLanguage(),
            phone,
            countryCode,
            newPassword
        )

    suspend fun getClinicById(lang: String, clinicId: String) = api.getClinicById(lang, clinicId)


    suspend fun addPet(petBody: PetBody): Response<MainResponse<Any>> {
        if (petBody.image != null) {
            val imagePart = MultipartBody.Part.createFormData(
                "image",
                petBody.image.name,
                petBody.image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
            return api.addPet(
                PrefsHelper.getToken(),
                PrefsHelper.getLanguage(),
                petBody.toMap(),
                imagePart
            )
        } else {
            return api.addPet(
                PrefsHelper.getToken(),
                PrefsHelper.getLanguage(),
                petBody,
            )
        }
    }

    suspend fun addRequest(
        addRequestBody: AddRequestBody, images: ImagesBody
    ): Response<MainResponse<Any>> {

        val imagesParts: MutableList<MultipartBody.Part> = mutableListOf()
        images.images!!.forEach {
            imagesParts.add(
                MultipartBody.Part.createFormData(
                    "images[]",
                    it.name,
                    it.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                )
            )
        }

        return api.addRequests(
            PrefsHelper.getToken(),
            PrefsHelper.getLanguage(),
            addRequestBody.toMap(),
            imagesParts.toTypedArray()
        )
    }

    suspend fun getRequestTypes() = api.getRequestTypes(PrefsHelper.getLanguage())

    suspend fun getConsultantById(requestId: String) =
        api.getConsultantById(PrefsHelper.getToken(), PrefsHelper.getLanguage(), requestId)

    suspend fun cancelConsultant(requestId: String) =
        api.cancelConsultant(PrefsHelper.getToken(), PrefsHelper.getLanguage(), requestId)

    suspend fun addVaccination(petId: String, date: String, typeId: String) =
        api.addVaccination(PrefsHelper.getToken(), PrefsHelper.getLanguage(), petId, date, typeId)

    suspend fun getPets() = api.myPets(
        PrefsHelper.getToken(),
        PrefsHelper.getLanguage(),
        PrefsHelper.getUserId().toString()
    )

    suspend fun petById(petId: String) = api.petById(
        PrefsHelper.getToken(),
        PrefsHelper.getLanguage(),
        petId
    )

    suspend fun getPetTypes() = api.getPetTypes(PrefsHelper.getLanguage())

    suspend fun getVaccinationTypes() = api.getVaccinationTypes(PrefsHelper.getLanguage())
    suspend fun getCategories() = api.getCategories(PrefsHelper.getLanguage())
    suspend fun getVendorsByCategoryId(catId: String) =
        api.getVendorsByCategoryId(PrefsHelper.getLanguage(), catId)

    suspend fun getSubCategories(vendorId: String) =
        api.getSubCategory(PrefsHelper.getLanguage(), vendorId)

    suspend fun getProducts(subcategoryId: String) =
        api.getProducts(PrefsHelper.getLanguage(), subcategoryId)

    suspend fun getProductById(id: String) = api.getProductById(PrefsHelper.getLanguage(), id)

    suspend fun addToCart(productId: String, quantity: String) = api.addToCart(
        PrefsHelper.getToken(),
        PrefsHelper.getLanguage(),
        PrefsHelper.getUserId().toString(),
        productId,
        quantity
    )

    suspend fun getCart() = api.getCart(
        PrefsHelper.getToken(),
        PrefsHelper.getLanguage(),
        PrefsHelper.getUserId().toString(),
    )

    suspend fun deleteItemFromCart(cartItemId: Int) =
        api.deleteItemFromCart(
            PrefsHelper.getToken(),
            PrefsHelper.getLanguage(),
            cartItemId
        )

    suspend fun updateCart(cartItemId: Int, quantity: Int) =
        api.updateCart(
            PrefsHelper.getToken(),
            PrefsHelper.getLanguage(),
            cartItemId,
            quantity
        )

    suspend fun search(name: String) =
        api.search(PrefsHelper.getLanguage(), name)

    suspend fun review(clinicId: String, rating: String, comment: String) =
        api.review(
            PrefsHelper.getToken(),
            PrefsHelper.getLanguage(),
            ReviewBody(
                PrefsHelper.getUserId().toString(),
                clinicId,
                rating,
                comment.ifEmpty { "None" },
            )
        )
}