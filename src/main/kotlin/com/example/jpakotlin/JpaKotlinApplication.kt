package com.example.jpakotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.UUID
import javax.annotation.PostConstruct
import javax.persistence.Entity
import javax.persistence.Id

@SpringBootApplication
class JpaKotlinApplication

fun main(args: Array<String>) {
    runApplication<JpaKotlinApplication>(*args)
}

@Entity
class Article(
    var name: ArticleName,
) {
    @Id var id: UUID = UUID.randomUUID()
}

@JvmInline
value class ArticleName(val name: String)

@Repository
interface ArticleRepository : JpaRepository<Article, UUID> {
    fun findByName(name: ArticleName): Article?
}

@Service
class TestService(private val articleRepository: ArticleRepository) {
    @PostConstruct
    fun init() {
        articleRepository.save(Article(ArticleName("foo")))
        println(articleRepository.findByName(ArticleName("foo")))
    }
}
