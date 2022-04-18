package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysPeerMapper;
import com.ruoyi.system.domain.SysPeer;
import com.ruoyi.system.service.ISysPeerService;

/**
 * 对等成员Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-04-04
 */
@Service
public class SysPeerServiceImpl implements ISysPeerService 
{
    @Autowired
    private SysPeerMapper sysPeerMapper;

    /**
     * 查询对等成员
     * 
     * @param id 对等成员主键
     * @return 对等成员
     */
    @Override
    public SysPeer selectSysPeerById(Long id)
    {
        return sysPeerMapper.selectSysPeerById(id);
    }

    /**
     * 查询对等成员列表
     * 
     * @param sysPeer 对等成员
     * @return 对等成员
     */
    @Override
    public List<SysPeer> selectSysPeerList(SysPeer sysPeer)
    {
        return sysPeerMapper.selectSysPeerList(sysPeer);
    }

    /**
     * 新增对等成员
     * 
     * @param sysPeer 对等成员
     * @return 结果
     */
    @Override
    public int insertSysPeer(SysPeer sysPeer)
    {
        sysPeer.setCreateTime(DateUtils.getNowDate());
        return sysPeerMapper.insertSysPeer(sysPeer);
    }

    /**
     * 修改对等成员
     * 
     * @param sysPeer 对等成员
     * @return 结果
     */
    @Override
    public int updateSysPeer(SysPeer sysPeer)
    {
        return sysPeerMapper.updateSysPeer(sysPeer);
    }

    /**
     * 批量删除对等成员
     * 
     * @param ids 需要删除的对等成员主键
     * @return 结果
     */
    @Override
    public int deleteSysPeerByIds(Long[] ids)
    {
        return sysPeerMapper.deleteSysPeerByIds(ids);
    }

    /**
     * 删除对等成员信息
     * 
     * @param id 对等成员主键
     * @return 结果
     */
    @Override
    public int deleteSysPeerById(Long id)
    {
        return sysPeerMapper.deleteSysPeerById(id);
    }
}
