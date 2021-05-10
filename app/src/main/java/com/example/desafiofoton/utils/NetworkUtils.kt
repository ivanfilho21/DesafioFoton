package com.example.desafiofoton.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {
    companion object {

        /**
         * Retorna uma instância única de Retrofit (singleton)
         * @param path Caminho principal da API
         */
        fun getRetrofitInstance(path : String) : Retrofit {
            return Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        /**
         * Retorna um caminho para uma imagem
         * @param path Caminho da imagem no servidor do TMDB
         * @param size Tamanho da imagem. Deve ser um desses:
         * "92", "154", "185", "342", "500", "780" ou "original".
         */
        fun getImageUrl(path : String?, size : String) : String {
            return "http://image.tmdb.org/t/p/w${size}${path}"
        }
    }
}