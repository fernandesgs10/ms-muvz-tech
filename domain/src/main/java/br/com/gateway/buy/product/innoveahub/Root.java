package br.com.gateway.buy.product.innoveahub;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Root {
    public String status;
    public int totalResults;
    public ArrayList<Article> articles;
}
