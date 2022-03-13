package tm.booup.tellme.repository;

import org.apache.ibatis.annotations.Mapper;
import tm.booup.tellme.domain.entity.TMUserEntity;

@Mapper
public interface UserRepository {

  TMUserEntity findByEmail(String email);

  TMUserEntity findById(String userId);

  int insertUser(TMUserEntity userEntity);

  int updateUserStatus(TMUserEntity userEntity);
}
