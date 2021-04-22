This repository is a demonstrated for an issue between kotlin inline classes and spring data.  
[Here's the issue in Kotlin issue tracker](https://youtrack.jetbrains.com/issue/KT-46251)

## The Problem
Accessing a property of an inline class with a spring data repository like
```kotlin
@Repository
interface ArticleRepository : JpaRepository<Article, UUID> {
    fun findByName(name: ArticleName): Article?
}
```
is not working because kotlin flavors the resulting java method with a suffix like this:
```java
public interface ArticleRepository extends JpaRepository {
   @Nullable
   Article findByName_q4Vv0d8/* $FF was: findByName-q4Vv0d8*/(@NotNull String var1);
}
```

This will cause an exception when Spring Data tries to generate the query for that method.

<pre>
Error creating bean with name 'articleRepository' defined in com.example.jpakotlin.ArticleRepository defined in @EnableJpaRepositories declared on JpaRepositoriesRegistrar.EnableJpaRepositoriesConfiguration: Invocation of init method failed; nested exception is java.lang.IllegalArgumentException: Failed to create query for method public abstract com.example.jpakotlin.Article com.example.jpakotlin.ArticleRepository.findByName-q4Vv0d8(java.lang.String)! No property name-q4Vv0d8 found for type Article!
</pre>
