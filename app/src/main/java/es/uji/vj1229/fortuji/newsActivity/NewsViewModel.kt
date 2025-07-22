package es.uji.vj1229.fortuji.newsActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.uji.vj1229.fortuji.common.News
import es.uji.vj1229.fortuji.network.FortniteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {

    var imageURL: String? = null
    val news = ArrayList<News>()
    var selectedNews: News?= null

    var view: NewsView? = null
        set(value){
            field=value
            if (value != null && news.isNotEmpty()) {
                showNews()
            }
        }
    init{

        loadNews()

    }

    fun showNews() =
        view?.apply{
            showImage(imageURL)
            showNews(news)
        }


    private fun loadNews() {
        viewModelScope.launch(Dispatchers.Main) {
            FortniteRepository.getNews()
                .onSuccess {
                    imageURL = it.first
                    news.clear()
                    news.addAll(it.second)

                    showNews()

                }
                .onFailure {
                    view?.showError("Error loading news")
                }
        }
    }

    fun onNewsSelected(news: News) {
        selectedNews = news
        view?.showCurrentNewsDetails()
    }

}