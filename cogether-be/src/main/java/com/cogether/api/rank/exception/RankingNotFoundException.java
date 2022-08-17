package com.cogether.api.rank.exception;

import javax.persistence.EntityNotFoundException;

public class RankingNotFoundException extends EntityNotFoundException {
    public RankingNotFoundException() {
        super("존재하지 않는 랭킹입니다. ");
    }
}
