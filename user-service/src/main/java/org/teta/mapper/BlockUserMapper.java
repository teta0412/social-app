package org.teta.mapper;

import dto.HeaderResponse;
import mapper.BasicMapper;
import org.teta.dto.response.BlockedUserResponse;
import org.teta.repository.projection.BlockedUserProjection;
import org.teta.service.BlockUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlockUserMapper {

    private final BasicMapper basicMapper;
    private final BlockUserService blockUserService;

    public HeaderResponse<BlockedUserResponse> getBlockList(Pageable pageable) {
        Page<BlockedUserProjection> blockList = blockUserService.getBlockList(pageable);
        return basicMapper.getHeaderResponse(blockList, BlockedUserResponse.class);
    }

    public Boolean processBlockList(Long userId) {
        return blockUserService.processBlockList(userId);
    }
}
