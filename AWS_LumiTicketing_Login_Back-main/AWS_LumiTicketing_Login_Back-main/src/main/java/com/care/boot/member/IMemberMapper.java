package com.care.boot.member;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface IMemberMapper {

    ArrayList<MemberDTO> memberInfo(@Param("begin") int begin, @Param("end") int end,
                                    @Param("select") String select, @Param("search") String search);

    @Select("SELECT COUNT(*) FROM VIPMember")
    int countVIPMembers();

    @Insert("INSERT INTO RegularMember (id, pw, userName, mobile, membership) VALUES (#{id}, #{pw}, #{userName}, #{mobile}, #{membership})")
    int registProc(MemberDTO member);

    @Select("(SELECT id, pw, userName, mobile, membership, NULL AS vip_number FROM RegularMember WHERE id = #{id}) " +
            "UNION ALL " +
            "(SELECT id, pw, userName, mobile, membership, vip_number FROM VIPMember WHERE id = #{id}) " +
            "UNION ALL " +
            "(SELECT id, pw, userName, mobile, membership, NULL AS vip_number FROM Admin WHERE id = #{id})")
    MemberDTO login(@Param("id") String id);




   

    
   

 

    @Select("SELECT id, userName, mobile, membership FROM RegularMember WHERE id = #{id} UNION ALL SELECT id, userName, mobile, membership FROM VIPMember WHERE id = #{id}")
    MemberDTO getMemberById(@Param("id") String id);

 



    
}
