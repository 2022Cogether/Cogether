package com.cogether.api.til.service;

import com.cogether.api.til.domain.Til;
import com.cogether.api.til.domain.TilImg;
import com.cogether.api.til.domain.TilRequest;
import com.cogether.api.til.domain.TilResponse;
import com.cogether.api.til.exception.TilNotFoundException;
import com.cogether.api.til.repository.TilImgRepository;
import com.cogether.api.til.repository.TilRepository;
import com.cogether.api.user.dto.User;
import com.cogether.api.user.exception.UserNotFoundException;
import com.cogether.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TilService {

    private final TilRepository tilRepository;
    private final TilImgRepository tilImgRepository;
    private final UserRepository userRepository;
    public TilResponse.OnlyId create(TilRequest.Create_Til create_til, TilRequest.Create_TilImg create_img){
        User user = userRepository.findById(create_til.getUserId()).orElseThrow(UserNotFoundException::new);
        Til til = create_til.toEntity(user);
        Til savedTil = tilRepository.save(til);
        for (int i = 0; i < create_img.getImgList().size(); i++){
            //TODO: 이미지 url 변환 작업
            String img_url = "";
            TilImg tilimg = TilImg.toEntity(savedTil, img_url);
            TilImg savedTilImg = tilImgRepository.save(tilimg);
        }
        return TilResponse.OnlyId.build(savedTil);
    }

    public TilResponse.OnlyId update(TilRequest.Update_Til update_til){
        Til til = tilRepository.findById(update_til.getTilId()).orElseThrow(TilNotFoundException::new);
        til.setTitle(update_til.getTitle());
        til.setContent(update_til.getContent());
        Til savedTil = tilRepository.save(til);
        return TilResponse.OnlyId.build(savedTil);
    }

    public TilResponse.OnlyId delete(int tilId){
        Til til = tilRepository.findById(tilId).orElseThrow(TilNotFoundException::new);
        tilRepository.deleteById(tilId);
        return TilResponse.OnlyId.build(til);
    }

}
