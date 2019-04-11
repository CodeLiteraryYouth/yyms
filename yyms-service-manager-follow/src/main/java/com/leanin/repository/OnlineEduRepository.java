package com.leanin.repository;

import com.leanin.domain.vo.OnlineEdu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineEduRepository extends JpaRepository<OnlineEdu,String> {
    OnlineEdu findByEduId(String eduId);
}
