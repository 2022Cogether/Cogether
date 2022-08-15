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
    public ResponseEntity<RankingResponse.TilRankList> getTilRank(@RequestParam int userId, @RequestParam int page){
        RankingResponse.TilRankList response = rankService.getTilRank(userId, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/til/follow")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.TilRankList> getMyTilRank(@RequestParam int userId, @RequestParam int page){
        RankingResponse.TilRankList response = rankService.getMyTilRank(userId, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/exp")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.ExpRankList> getExpList(@RequestParam int userId, @RequestParam int page){
        RankingResponse.ExpRankList response = rankService.getExpRank(userId, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/exp/follow")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.ExpRankList> getMyExpList(@RequestParam int userId, @RequestParam int page){
        RankingResponse.ExpRankList response = rankService.getMyExpRank(userId, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/total")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.LiveCompRankList> getTotalRank(@RequestParam int userId, @RequestParam int page){
        RankingResponse.LiveCompRankList response = rankService.getTotalRank(userId, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/total/follow")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.LiveCompRankList> getMyTotalRank(@RequestParam int userId, @RequestParam int page){
        RankingResponse.LiveCompRankList response = rankService.getMyTotalRank(userId, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/week")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.LiveCompRankList> getWeekRank(@RequestParam int userId, @RequestParam int page){
        RankingResponse.LiveCompRankList response = rankService.getWeekRank(userId, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/week/follow")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.LiveCompRankList> getMyWeekRank(@RequestParam int userId, @RequestParam int page){
        RankingResponse.LiveCompRankList response = rankService.getMyWeekRank(userId, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/month")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.LiveCompRankList> getMonthRank(@RequestParam int userId, @RequestParam int page){
        RankingResponse.LiveCompRankList response = rankService.getMonthRank(userId, page);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/ranking/month/follow")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<RankingResponse.LiveCompRankList> getMyMonthRank(@RequestParam int userId, @RequestParam int page){
        RankingResponse.LiveCompRankList response = rankService.getMyMonthRank(userId, page);
        return ResponseEntity.ok().body(response);
    }

}
