package br.com.gateway.buy.product.repository;

import br.com.gateway.buy.product.entity.AddressEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    @Query("select a from AddressEntity a")
    Page<AddressEntity> findAddressAll(Pageable pageable);

    @Query("select a from AddressEntity a where a.nmAddressZipCode = :nmAddressZipCode")
    Optional<AddressEntity> findAddressBYNmAddressZipCode(@Param("nmAddressZipCode") String nmAddressZipCode);

}
