package com.intelbras.urna

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import java.util.UUID

data class Article(
    val id: UUID,
    val title: String,
    val authorName: String
)

data class Author(
    val id: UUID,
    val name: String
)

val listArticle = listOf(
    Article(UUID.randomUUID(), "Title 1", "Author 1"),
    Article(UUID.randomUUID(), "Title 2", "Author 1"),
    Article(UUID.randomUUID(), "Title 3", "Author 2"),
    Article(UUID.randomUUID(), "Title 4", "Author 2"),
    Article(UUID.randomUUID(), "Title 5", "Author 2"),
    Article(UUID.randomUUID(), "Title 6", "Author 3"),
)

val listAuthor = listOf(
    Author(UUID.randomUUID(), "Author 1"),
    Author(UUID.randomUUID(), "Author 2"),
    Author(UUID.randomUUID(), "Author 3"),
)

class ArticlesProducer {
    fun articles(): Flow<List<Article>> {
        return listOf(listArticle).asFlow()
    }
}

class AuthorProducer {
    fun authors(): Flow<List<Author>> {
        return listOf(listAuthor).asFlow()
    }
}

data class ArticleFullInformation(
    val article: Article,
    val author: Author
)


private val articlesProducer = ArticlesProducer()
private val authorProducer = AuthorProducer()


class ArticlesIntermediary {
    fun articles(): Flow<List<ArticleFullInformation>> {
        return articlesProducer.articles().combine(authorProducer.authors()) { articles, authors ->
            articles.map { article ->
                ArticleFullInformation(
                    article = article,
                    author = authors.first { author -> author.name == article.authorName }
                )

            }
        }
    }
}
















