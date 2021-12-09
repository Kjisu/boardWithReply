package org.zerock.boardWithReply.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity{

    //이메일(pk),비번,이름

    @Id
    private String email;
    private String password;
    private String name;



}
