package com.ncw809.ordermanagementgui.commons;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {
	@FormUrlEncoded
	@POST("auth/login")
	Call<LoginResponseFormat> login(
		@Field("id") String id,
		@Field("password") String password);

	@FormUrlEncoded
	@POST("auth/signup")
	Call<ResponseFormat> signup(
		@Field("id") String id,
		@Field("password1") String password1,
		@Field("password2") String password2,
		@Field("phoneNumber") String phoneNumber,
		@Field("name") String name,
		@Field("role") String role);

	@GET("auth/authorityCheck")
	Call<AuthorityCheckResponseFormat> authorityCheck(@Header("Authorization") String token);

	@GET("menu/store/{storeSeq}")
	Call<MenuListResponseFormat> getMenuListByStoreSeq(
		@Path("storeSeq") Long storeSeq,
		@Header("Authorization") String token);

	@GET("store/list")
	Call<StoreListResponseFormat> getStoreList(@Header("Authorization") String token);

	@FormUrlEncoded
	@POST("visit")
	Call<SaveVisitResponseFormat> saveVisit(
		@Header("Authorization") String token,
		@Field("storeSeq") Long storeSeq);

	@FormUrlEncoded
	@POST("order")
	Call<ResponseFormat> saveOrder(
		@Header("Authorization") String token,
		@Field("orders") String orders,
		@Field("paymentMethod") String paymentMethod,
		@Field("platform") String platform,
		@Field("totalPrice") Long totalPrice,
		@Field("joinSeq") Long joinSeq,
		@Field("storeSeq") Long storeSeq
	);

	@GET("table/store/{storeSeq}")
	Call<TableListResponseFormat> getTableListByStoreSeq(
		@Path("storeSeq") Long storeSeq,
		@Header("Authorization") String token
	);

	@FormUrlEncoded
	@POST("reservation")
	Call<ResponseFormat> addReservation(
		@Header("Authorization") String token,
		@Field("covers") Long covers,
		@Field("reservationTime") LocalDateTime reservationTime,
		@Field("storeSeq") Long storeSeq
	);
}
