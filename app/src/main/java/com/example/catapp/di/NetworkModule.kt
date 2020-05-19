import com.example.catapp.api.AuthInterceptor
import com.example.catapp.api.CatBreedApiService
import com.example.catapp.api.LoginApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    factory { AuthInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideCatBreedsApi(get()) }
    single { provideRetrofit(get()) }
    single { provideLoginApi() }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/v1/").client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}

fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()
}

fun provideCatBreedsApi(retrofit: Retrofit): CatBreedApiService =
    retrofit.create(CatBreedApiService::class.java)

fun provideLoginApi(): LoginApiService = LoginApiService()