package br.com.gateway.buy.product.repository;

import br.com.gateway.buy.product.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    @Query("select c from ClientEntity c")
    Page<ClientEntity> findClientAll(Pageable pageable);

    @Query("select c from ClientEntity c where c.nmName = :nmName")
    Optional<ClientEntity> findClientBYNmName(@Param("nmName") String nmName);

}
