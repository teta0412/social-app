package org.teta.controller.rest;

import org.teta.constants.PathConstants;
import org.teta.dto.HeaderResponse;
import org.teta.dto.response.MutedUserResponse;
import org.teta.mapper.MuteUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.UI_V1_USER)
public class MuteUserController {

    private final MuteUserMapper muteUserMapper;

    @GetMapping(PathConstants.MUTED)
    public ResponseEntity<List<MutedUserResponse>> getMutedList(@PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<MutedUserResponse> response = muteUserMapper.getMutedList(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(PathConstants.MUTED_USER_ID)
    public ResponseEntity<Boolean> processMutedList(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(muteUserMapper.processMutedList(userId));
    }
}
