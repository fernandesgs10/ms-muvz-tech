package br.com.gateway.buy.product.innoveahub;

import lombok.Data;

import java.util.Date;

@Data
public class Article {
    public Source source;
    public String author;
    public String title;
    public String description;
    public String url;
    public String urlToImage;
    public Date publishedAt;
    public String content;
}
