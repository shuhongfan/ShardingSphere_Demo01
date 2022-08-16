package com.shf.shardingjdbcdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shf.shardingjdbcdemo.entity.Course;
import com.shf.shardingjdbcdemo.entity.Udict;
import com.shf.shardingjdbcdemo.entity.User;
import com.shf.shardingjdbcdemo.mapper.CourseMapper;
import com.shf.shardingjdbcdemo.mapper.UdictMapper;
import com.shf.shardingjdbcdemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShardingJdbcDemoApplicationTests {

    //注入mapper
    @Autowired
    private CourseMapper courseMapper;

    //注入user的mapper
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UdictMapper udictMapper;

    //======================测试公共表===================
    //添加操作
    @Test
    public void addDict() {
        Udict udict = new Udict();
        udict.setUstatus("a");
        udict.setUvalue("已启用");
        udictMapper.insert(udict);
    }

    //查询操作
    @Test
    public void selectDict() {
        Udict udict = new Udict();
        QueryWrapper<Udict> udictQueryWrapper = new QueryWrapper<>();
        udictQueryWrapper.eq("dictid",766345681827790849L);
        Udict u = udictMapper.selectOne(udictQueryWrapper);
        System.out.println(u);
    }

    //删除操作
    @Test
    public void deleteDict() {
        QueryWrapper<Udict> wrapper = new QueryWrapper<>();
        //设置userid值
        wrapper.eq("dictid",766345681827790849L);
        udictMapper.delete(wrapper);
    }

    //======================测试垂直分库==================
    //添加操作
    @Test
    public void addUserDb() {
        User user = new User();
        user.setUsername("lucymary");
        user.setUstatus("a");
        userMapper.insert(user);
    }

    //查询操作
    @Test
    public void findUserDb() {
        QueryWrapper<User>  wrapper = new QueryWrapper<>();
        //设置userid值
        wrapper.eq("user_id",766343079278936065L);
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }


    //======================测试水平分库=====================
    //添加操作
    @Test
    public void addCourseDb() {
        Course course = new Course();
        course.setCname("javademo1");
        //分库根据user_id
        course.setUserId(111L);
        course.setCstatus("Normal1");
        courseMapper.insert(course);
    }

    //查询操作
    @Test
    public void findCourseDb() {
        QueryWrapper<Course>  wrapper = new QueryWrapper<>();
        //设置userid值
        wrapper.eq("user_id",100L);
        //设置cid值
        wrapper.eq("cid",766338540224118785L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
    }

    //=======================测试水平分表===================
    //添加课程的方法
    @Test
    public void addCourse() {
        for(int i=1;i<=10;i++) {
            Course course = new Course();
            course.setCname("java"+i);
            course.setUserId(100L);
            course.setCstatus("Normal"+i);
            courseMapper.insert(course);
        }
    }
    //查询课程的方法
    @Test
    public void findCourse() {
        QueryWrapper<Course>  wrapper = new QueryWrapper<>();
        wrapper.eq("cid",766331633002348544L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
    }


}
