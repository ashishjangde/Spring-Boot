Spring Security
Oauth2 Client
Authentication

\section*{Configure OAuth2}

```
Add the dependency:
<dependency>
    <groupld>org.springframework.boot</groupld>
    <artifactld>spring-boot-starter-oauth2-client</artifactld>
</dependency>
Configure the WebSecurityConfig
.oauth2Login(oauth2Login ->
    oauth2Login
    .failureUrl("/login?error=true")
    .successHandler(oAuth2SuccessHandler)
)
```

\section*{Third Party Authentication}

![](https://cdn.mathpix.com/cropped/2024_08_22_17174bddf1205cbb0afdg-3.jpg?height=1265&width=1782&top_left_y=371&top_left_x=806)


