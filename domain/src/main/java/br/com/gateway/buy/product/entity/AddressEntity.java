package br.com.gateway.buy.product.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tb_address")
public class AddressEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_seq_address", unique = true, nullable = false)
    private Long coSeqAddress;

    @Column(name = "nm_address", nullable = false, length = 100)
    private String nmAddress;

    @Column(name = "nm_address_zip_code", length = 10)
    private String nmAddressZipCode;

    @Column(name = "nm_address_number", length = 5)
    private String nmAddressNumber;

    @Column(name = "nm_address_city_code", length = 20)
    private String nmAddressCityCode;

    @Column(name = "nm_address_complement", length = 10)
    private String nmAddressComplement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_seq_client")
    @JsonBackReference
    @ToString.Exclude
    private ClientEntity clientEntity;

}