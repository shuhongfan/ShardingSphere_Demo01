package com.shf.shardingjdbcdemo.mapper;

import com.shf.shardingjdbcdemo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
