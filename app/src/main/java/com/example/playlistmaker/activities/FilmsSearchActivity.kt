package com.example.playlistmaker.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.adapters.MovieAdapter
import com.example.playlistmaker.model.MovieResponse
import com.example.playlistmaker.R
import com.example.playlistmaker.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmsSearchActivity : AppCompatActivity() {

    // Адаптер для RecyclerView
    private lateinit var movieAdapter: MovieAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Подключаем layout activity_main.xml
        setContentView(R.layout.activity_search_film)

        // Находим элементы на экране по id
        val searchInput = findViewById<EditText>(R.id.searchFilmInput)
        val searchButton = findViewById<Button>(R.id.searchFilmsButton)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFilms)

        // Создаем адаптер
        // Пока список пустой
        movieAdapter = MovieAdapter(emptyList()) { movie ->

            val intent = Intent(this, PosterActivity::class.java)

            intent.putExtra("poster", movie.image)

            startActivity(intent)
        }

        // Указываем RecyclerView,
        // что элементы будут идти вертикальным списком
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Подключаем адаптер
        recyclerView.adapter = movieAdapter

        // Обработка нажатия на кнопку
        searchButton.setOnClickListener {

            // Получаем текст из поля поиска
            val query = searchInput.text.toString().trim()

            // Проверяем, что пользователь что-то ввел
            if (query.isNotEmpty()) {
                searchMovies(query)
            } else {
                Toast.makeText(
                    this,
                    "Введите название фильма",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }



    private fun searchMovies(query: String) {
        // Делаем запрос через Retrofit
        RetrofitClient.movieApi.searchMovies(query)
            .enqueue(object : Callback<MovieResponse> {

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {

                    if (response.isSuccessful) {

                        val movies = response.body()?.results ?: emptyList()

                        if (movies.isNotEmpty()) {

                            // Обновляем RecyclerView
                            movieAdapter.updateMovies(movies)

                        } else {
                            Toast.makeText(
                                this@FilmsSearchActivity,
                                "Ничего не найдено",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        Toast.makeText(
                            this@FilmsSearchActivity,
                            "Ошибка сервера",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<MovieResponse>,
                    t: Throwable
                ) {

                    Toast.makeText(
                        this@FilmsSearchActivity,
                        "Ошибка подключения: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}