package com.yufeng;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yufeng.entity.Course;
import com.yufeng.entity.Udict;
import com.yufeng.entity.User;
import com.yufeng.mapper.CourseMapper;
import com.yufeng.mapper.UdictMapper;
import com.yufeng.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingjdbcdemoApplicationTests {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UdictMapper udictMapper;

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
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        List<Course> course = courseMapper.selectList(wrapper);
        course.stream().forEach(c -> System.out.println(c.toString()));
    }


    //======================测试水平分库=====================
    //添加操作
    @Test
    public void addCourseDb() {
        Course course = new Course();
        course.setCname("javademo1");
        //分库根据user_id
        course.setUserId(101L);
        course.setCstatus("Normal");
        courseMapper.insert(course);
    }

    //查询操作
    @Test
    public void findCourseDb() {
        QueryWrapper<Course>  wrapper = new QueryWrapper<>();
        //设置userid值
        wrapper.eq("user_id",100L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println("100: " + course);

        wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",101L);
        course = courseMapper.selectOne(wrapper);
        System.out.println("101: " + course);

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
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(u -> System.out.println(u));
    }

    //======================测试公共表===================
    //添加操作
    @Test
    public void addDict() {
        Udict udict = new Udict();
        udict.setUstatus("a");
        udict.setUvalue("已启用");
        udictMapper.insert(udict);
    }

    //删除操作
    @Test
    public void deleteDict() {
        QueryWrapper<Udict>  wrapper = new QueryWrapper<>();
        udictMapper.delete(wrapper);
    }
}
