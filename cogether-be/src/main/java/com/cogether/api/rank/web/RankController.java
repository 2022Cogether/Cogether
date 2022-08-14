package com.cogether.api.rank.web;


import com.cogether.api.rank.domain.RankingResponse;
import com.cogether.api.rank.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class RankController {

    private final RankService rankService;

    @GetMapping(path = "/ranking/til")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.TilRankList> getTilRank(@RequestParam int userId, int page){
        RankingResponse.TilRankList response = rankService.getTilRank(userId, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/til/follow")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.TilRankList> getMyTilRank(@RequestParam int userId, int page){
        RankingResponse.TilRankList response = rankService.getMyTilRank(userId, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/exp")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.ExpRankList> getExpList(@RequestParam int userId, @RequestParam int page){
        RankingResponse.ExpRankList response = rankService.getExpList(userId, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/exp/follow")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.ExpRankList> getMyExpList(@RequestParam int userId, @RequestParam int page){
        RankingResponse.ExpRankList response = rankService.getMyExpList(userId, page);
        return ResponseEntity.ok().body(response);
    }

}
