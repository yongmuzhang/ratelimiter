package com.zym.ratelimiter.model;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by zym on 17/1/24.
 */
public class TokenBucket {
    private String name;
    private Integer length;
    private Jedis jedis;

    public TokenBucket(String name, Integer length, Jedis jedis) {
        this.name = name;
        this.length = length;
        this.jedis = jedis;
    }

    public Integer push(Token token) {
        if (token == null) {
            return 0;
        } else {
            Long size = jedis.llen(this.name);
            if (size != null && size < this.length) {
                jedis.lpush(this.name, token.getName());
                return 1;
            } else {
                return 0;
            }
        }
    }

    public Integer push(List<Token> tokens) {
        if (tokens != null && tokens.size() > 0) {
            Long size = jedis.llen(this.name);
            if ((tokens.size() + size) < this.length) {
                for (Token token : tokens) {
                    jedis.lpush(this.name, token.getName());
                }
                return tokens.size();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public Token pop() {
        String tokeName = jedis.lpop(this.name);
        if (tokeName != null) {
            return new Token(tokeName);
        } else {
            return null;
        }
    }

}
