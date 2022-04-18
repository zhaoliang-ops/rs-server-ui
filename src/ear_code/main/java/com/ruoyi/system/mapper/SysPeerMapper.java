package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysPeer;

/**
 * 对等成员Mapper接口
 * 
 * @author ruoyi
 * @date 2022-04-04
 */
public interface SysPeerMapper 
{
    /**
     * 查询对等成员
     * 
     * @param id 对等成员主键
     * @return 对等成员
     */
    public SysPeer selectSysPeerById(Long id);

    /**
     * 查询对等成员列表
     * 
     * @param sysPeer 对等成员
     * @return 对等成员集合
     */
    public List<SysPeer> selectSysPeerList(SysPeer sysPeer);

    /**
     * 新增对等成员
     * 
     * @param sysPeer 对等成员
     * @return 结果
     */
    public int insertSysPeer(SysPeer sysPeer);

    /**
     * 修改对等成员
     * 
     * @param sysPeer 对等成员
     * @return 结果
     */
    public int updateSysPeer(SysPeer sysPeer);

    /**
     * 删除对等成员
     * 
     * @param id 对等成员主键
     * @return 结果
     */
    public int deleteSysPeerById(Long id);

    /**
     * 批量删除对等成员
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysPeerByIds(Long[] ids);
}
