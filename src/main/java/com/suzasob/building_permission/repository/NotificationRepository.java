package com.suzasob.building_permission.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suzasob.building_permission.model.Notification;
import com.suzasob.building_permission.model.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipient(User user);
    List<Notification> findBySentAtBetween(Date start, Date end);
}
 