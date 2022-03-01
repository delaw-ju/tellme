package tm.booup.tellme.repository;

import org.apache.ibatis.annotations.Mapper;
import tm.booup.tellme.domain.entity.TMUserEntity;

@Mapper
public interface UserRepository {

  TMUserEntity findById(TMUserEntity userEntity);

  int insertUser(TMUserEntity userEntity);

}
