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

    @GetMapping(path = "/ranking/til/{page}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.TilRankList> getTilRank(@RequestHeader("ACCESS_TOKEN") String token, @PathVariable int page){
        RankingResponse.TilRankList response = rankService.getTilRank(token, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/til/follow/{page}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.TilRankList> getMyTilRank(@RequestHeader("ACCESS_TOKEN") String token, @PathVariable int page){
        RankingResponse.TilRankList response = rankService.getMyTilRank(token, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/exp/{page}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.ExpRankList> getExpList(@RequestHeader("ACCESS_TOKEN") String token, @PathVariable int page){
        RankingResponse.ExpRankList response = rankService.getExpRank(token, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/exp/follow/{page}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.ExpRankList> getMyExpList(@RequestHeader("ACCESS_TOKEN") String token, @PathVariable int page){
        RankingResponse.ExpRankList response = rankService.getMyExpRank(token, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/total/{page}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.LiveCompRankList> getTotalRank(@RequestHeader("ACCESS_TOKEN") String token, @PathVariable int page){
        RankingResponse.LiveCompRankList response = rankService.getTotalRank(token, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/total/follow/{page}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.LiveCompRankList> getMyTotalRank(@RequestHeader("ACCESS_TOKEN") String token, @PathVariable int page){
        RankingResponse.LiveCompRankList response = rankService.getMyTotalRank(token, page);
        return ResponseEntity.ok().body(response);
    }

//    @GetMapping(path = "/ranking/week/{page/{page}}")
//    @ResponseStatus(value = HttpStatus.OK)
//    public ResponseEntity<RankingResponse.LiveCompRankList> getWeekRank(@RequestHeader("ACCESS_TOKEN") String token, @PathVariable int page){
//        RankingResponse.LiveCompRankList response = rankService.getWeekRank(token, page);
//        return ResponseEntity.ok().body(response);
//    }
//
//    @GetMapping(path = "/ranking/week/follow/{page}")
//    @ResponseStatus(value = HttpStatus.OK)
//    public ResponseEntity<RankingResponse.LiveCompRankList> getMyWeekRank(@RequestHeader("ACCESS_TOKEN") String token, @PathVariable int page){
//        RankingResponse.LiveCompRankList response = rankService.getMyWeekRank(token, page);
//        return ResponseEntity.ok().body(response);
//    }
//
//    @GetMapping(path = "/ranking/month/{page}")
//    @ResponseStatus(value = HttpStatus.OK)
//    public ResponseEntity<RankingResponse.LiveCompRankList> getMonthRank(@RequestHeader("ACCESS_TOKEN") String token, @PathVariable int page){
//        RankingResponse.LiveCompRankList response = rankService.getMonthRank(token, page);
//        return ResponseEntity.ok().body(response);
//    }
//
//    @GetMapping(path = "/ranking/month/follow/{page})
//    @ResponseStatus(value = HttpStatus.OK)
//    public ResponseEntity<RankingResponse.LiveCompRankList> getMyMonthRank(@RequestHeader("ACCESS_TOKEN") String token, @PathVariable int page){
//        RankingResponse.LiveCompRankList response = rankService.getMyMonthRank(token, page);
//        return ResponseEntity.ok().body(response);
//    }

}
