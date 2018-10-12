package com.github.izerui.file.service;

import com.ecworking.audit.Record;
import com.github.izerui.file.entity.UserStatistical;
import com.github.izerui.file.repository.StIgnoreTypeRepository;
import com.github.izerui.file.repository.UserStatisticalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StService {

    @Autowired
    private UserStatisticalRepository userStatisticalRepository;

    @Autowired
    private StIgnoreTypeRepository stIgnoreTypeRepository;

    @Async
    public void logCount(Record record) {
        //如果在排除列表就删除同类型数据，不再记录
        if (stIgnoreTypeRepository.count(record.getApplication(), record.getType(), record.getName()) > 0L) {
            userStatisticalRepository.delete(record.getApplication(), record.getType(), record.getName());
            return;
        }

        UserStatistical st;
        long count = userStatisticalRepository.count(record.getUserCode(), record.getApplication(), record.getType(), record.getName());
        if (count > 0L) {
            st = userStatisticalRepository.get(record.getUserCode(), record.getApplication(), record.getType(), record.getName());
        } else {
            st = new UserStatistical();
        }
        st.setApplication(record.getApplication());
        st.setType(record.getType());
        st.setSignature(record.getSignature());
        st.setName(record.getName());
        st.setUrl(record.getUrl());
        st.setAccountCode(record.getAccountCode());
        st.setAccountName(record.getAccountName());
        st.setUserCode(record.getUserCode());
        st.setUserName(record.getUserName());
        st.setEntCode(record.getEntCode());
        st.setEntName(record.getEntName());
        if (record.getName() != null && record.getName().equals("登录系统")) {
            st.setLastLoginTime(record.getBegin());
        }
        st.setLastOptTime(record.getBegin());
        st.setCount(st.getCount() + 1);
        userStatisticalRepository.save(st);
    }



}
