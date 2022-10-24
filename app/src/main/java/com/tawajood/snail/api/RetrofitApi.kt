package com.tawajood.snail.api


import com.tawajood.snail.pojo.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Response
import retrofit2.http.*

interface RetrofitApi {

    companion object {
        const val BASE_URL = "https://snail.horizon.net.sa/api/"
    }


    @POST("register")
    suspend fun register(
        @Header("lang") lang: String,
        @Body registerBody: RegisterBody
    ): Response<MainResponse<UserResponse>>

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Header("lang") lang: String,
        @Field("country_code") countryCode: String,
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Response<MainResponse<UserResponse>>


    @FormUrlEncoded
    @POST("check-phone")
    suspend fun checkPhone(
        @Header("lang") lang: String,
        @Field("country_code") countryCode: String,
        @Field("phone") phone: String
    ): Response<MainResponse<Exist>>

    @GET("most-rated-clinics")
    suspend fun getSlider(
        @Header("lang") lang: String
    ): Response<MainResponse<SliderResponse>>


    @FormUrlEncoded
    @POST("my-requests")
    suspend fun getMyConsultants(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Field("user_id") user_id: String,
    ): Response<MainResponse<ConsultantsResponse>>

    @Multipart
    @JvmSuppressWildcards
    @POST("add-request")
    suspend fun addRequests(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @PartMap requestBody: Map<String, RequestBody>,
        @Part images: Array<MultipartBody.Part>
    ): Response<MainResponse<Any>>

    @FormUrlEncoded
    @POST("request-by-id")
    suspend fun getConsultantById(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Field("request_id") requestId: String,
    ): Response<MainResponse<ConsultantResponse>>

    @FormUrlEncoded
    @POST("cancel-request")
    suspend fun cancelConsultant(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Field("request_id") requestId: String,
    ): Response<MainResponse<Any>>

    @GET("request-types")
    suspend fun getRequestTypes(
        @Header("lang") lang: String,
    ): Response<MainResponse<RequestTypeResponse>>

    @GET("clinics")
    suspend fun getClinics(
        @Header("lang") lang: String
    ): Response<ClinicsResponse>

    @FormUrlEncoded
    @POST("get-clinic-by-id")
    suspend fun getClinicById(
        @Header("lang") lang: String,
        @Field("id") clinicId: String,
    ): Response<ClinicsResponse>

    @POST("rate-clinic")
    suspend fun review(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Body rateBody: ReviewBody
    ): Response<MainResponse<Any>>

    @FormUrlEncoded
    @POST("search-clinics")
    suspend fun search(
        @Header("lang") lang: String,
        @Field("name") name: String
    ): Response<ClinicsResponse>

    @Multipart
    @JvmSuppressWildcards
    @POST("add-pet")
    suspend fun addPet(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @PartMap addPet: Map<String, RequestBody>,
        @Part image: MultipartBody.Part
    ): Response<MainResponse<Any>>


    @POST("add-pet")
    suspend fun addPet(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Body addPet: PetBody,
    ): Response<MainResponse<Any>>


    @FormUrlEncoded
    @POST("my-pets")
    suspend fun myPets(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Field("user_id") user_id: String,
    ): Response<MainResponse<PetResponse>>

    @FormUrlEncoded
    @POST("pet-by-id")
    suspend fun petById(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Field("pet_id") pet_id: String,
    ): Response<MainResponse<PetByIdResponse>>

    @FormUrlEncoded
    @POST("add-vaccination")
    suspend fun addVaccination(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Field("pet_id") pet_id: String,
        @Field("date") date: String,
        @Field("type") type: String,
    ): Response<MainResponse<Any>>

    @GET("pet-types")
    suspend fun getPetTypes(
        @Header("lang") lang: String,
    ): Response<MainResponse<PetTypeResponse>>

    @GET("vaccination-types")
    suspend fun getVaccinationTypes(
        @Header("lang") lang: String,
    ): Response<MainResponse<VaccinationTypesResponse>>

    @GET("categories")
    suspend fun getCategories(
        @Header("lang") lang: String,
    ): Response<MainResponse<CategoriesResponse>>

    @GET("get-vendors-by-category-id")
    suspend fun getVendorsByCategoryId(
        @Header("lang") lang: String,
        @Query(value = "cat_id") cat_id: String,
    ): Response<MainResponse<GetVendorsByCategoryIdResponse>>

    @GET("get-subcategories-by-vendor-id")
    suspend fun getSubCategory(
        @Header("lang") lang: String,
        @Query(value = "vendor_id") vendor_id: String,
    ): Response<MainResponse<SubCategoriesResponse>>

    @GET("get-products-by-subcategory-id")
    suspend fun getProducts(
        @Header("lang") lang: String,
        @Query(value = "subcategory_id") subcategory_id: String,
    ): Response<MainResponse<ProductsResponse>>

    @POST("get-product-by-id")
    suspend fun getProductById(
        @Header("lang") lang: String,
        @Query(value = "id") id: String,
    ): Response<MainResponse<ProductResponse>>

    @FormUrlEncoded
    @POST("add-cart")
    suspend fun addToCart(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Field("user_id") user_id: String,
        @Field("product_id") product_id: String,
        @Field("quantity") quantity: String,
    ): Response<MainResponse<Any>>

    @FormUrlEncoded
    @POST("cart")
    suspend fun getCart(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Field("user_id") user_id: String,
    ): Response<MainResponse<CartResponse>>


    @POST("add-order")
    suspend fun addOrder(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Body orderBody: OrderBody
    ): Response<MainResponse<Any>>

    @FormUrlEncoded
    @POST("my-orders")
    suspend fun getOrders(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Field("user_id") user_id: String,
    ): Response<MainResponse<OrdersResponse>>

    @FormUrlEncoded
    @POST("order-by-id")
    suspend fun getOrderById(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Field("order_id") order_id: String,
    ): Response<MainResponse<OrderResponse>>

    @FormUrlEncoded
    @POST("order-delivered")
    suspend fun orderDelivered(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Field("order_id") order_id: String,
    ): Response<MainResponse<Any>>

    @FormUrlEncoded
    @POST("notifications")
    suspend fun getNotifications(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Field("user_id") userId: Int
    ): Response<MainResponse<NotificationsResponse>>

    @FormUrlEncoded
    @POST("update-fcm-token")
    suspend fun updateFCMToken(
        @Header("token") token: String,
        @Field("user_id") userId: Int,
        @Field("fcm_token") fcmToken: String,
        @Field("mobile_id") mobileId: Int
    )

    @FormUrlEncoded
    @POST("delete-cart")
    suspend fun deleteItemFromCart(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Field("cart_id") cartItemId: Int
    ): Response<MainResponse<Any>>

    @FormUrlEncoded
    @POST("update-cart")
    suspend fun updateCart(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Field("cart_id") cartItemId: Int,
        @Field("quantity") quantity: Int
    ): Response<MainResponse<Any>>


    @GET("profile")
    suspend fun getProfile(
        @Header("token") token: String,
        @Header("lang") lang: String,
    ): Response<MainResponse<ProfileResponse>>

    @Multipart
    @JvmSuppressWildcards
    @POST("update-profile")
    suspend fun updateProfile(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @PartMap updateProfileMap: Map<String, RequestBody>,
        @Part image: MultipartBody.Part
    ): Response<MainResponse<Any>>

    @POST("update-profile")
    suspend fun updateProfile(
        @Header("token") token: String,
        @Header("lang") lang: String,
        @Body updateBody: ProfileBody
    ): Response<MainResponse<Any>>

    @FormUrlEncoded
    @POST("rechangepass")
    suspend fun changePassword(
        @Header("lang") lang: String,
        @Field("phone") phone: String,
        @Field("country_code") countryCode: String,
        @Field("new_password") newPassword: String
    ): Response<MainResponse<Any>>

    @FormUrlEncoded
    @POST("get-identifiers")
    suspend fun getIdentifiers(
        @Header("token") token: String,
        @Field("user_id") userId: Int
    ): Response<MainResponse<Identifier>>

    @GET("setting/terms")
    suspend fun getTerms(
        @Header("lang") lang: String
    ): Response<MainResponse<Terms>>

    @GET("setting/about-us")
    suspend fun getAbout(
        @Header("lang") lang: String
    ): Response<MainResponse<About>>

    @GET("setting/contact-us")
    suspend fun getContact(
        @Header("lang") lang: String,
    ): Response<MainResponse<ContactResponse>>
}