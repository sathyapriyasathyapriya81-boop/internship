package com.pastelchat.repository;
import com.pastelchat.model.Message;
import com.pastelchat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("""
        SELECT m FROM Message m
        WHERE (m.sender = :a AND m.receiver = :b)
           OR (m.sender = :b AND m.receiver = :a)
        ORDER BY m.sentAt ASC
    """)
    List<Message> findConversation(User a, User b);
    long countBySenderAndReceiverAndReadFalse(User sender, User receiver);
    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.read = true WHERE m.sender = :sender AND m.receiver = :receiver AND m.read = false")
    void markAllRead(User sender, User receiver);
}