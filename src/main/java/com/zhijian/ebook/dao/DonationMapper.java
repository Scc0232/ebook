package com.zhijian.ebook.dao;

import com.zhijian.ebook.bean.Page;
import com.zhijian.ebook.entity.Donation;
import com.zhijian.ebook.entity.DonationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DonationMapper {
    int countByExample(DonationExample example);

    int deleteByExample(DonationExample example);

    int deleteByPrimaryKey(String id);

    int insert(Donation record);

    int insertSelective(Donation record);

    List<Donation> selectByExample(DonationExample example);

    Donation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Donation record, @Param("example") DonationExample example);

    int updateByExample(@Param("record") Donation record, @Param("example") DonationExample example);

    int updateByPrimaryKeySelective(Donation record);

    int updateByPrimaryKey(Donation record);

	List<Donation> findPaginationList(@Param("page")Page page,@Param("example") DonationExample donationExample);
}