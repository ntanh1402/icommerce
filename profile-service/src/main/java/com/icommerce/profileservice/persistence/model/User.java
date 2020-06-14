package com.icommerce.profileservice.persistence.model;

import com.icommerce.common.model.AbstractTimeBaseIdentifiable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {User.EMAIL_COLUMN}),
                                            @UniqueConstraint(columnNames = {User.FACEBOOK_ID_COLUMN}),
                                            @UniqueConstraint(columnNames = {User.GOOGLE_ID_COLUMN})})
public class User extends AbstractTimeBaseIdentifiable<User> {

    static final String EMAIL_COLUMN = "email";
    static final String FACEBOOK_ID_COLUMN = "facebookId";
    static final String GOOGLE_ID_COLUMN = "googleId";

    @Column(length = 500)
    private String email;

    private String phoneNumber;

    private String facebookId;

    private String googleId;

    @Override
    public boolean deepEquals(User user) {
        return Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber) &&
               Objects.equals(facebookId, user.facebookId) && Objects.equals(googleId, user.googleId);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(email, phoneNumber, facebookId, googleId);
    }

}
