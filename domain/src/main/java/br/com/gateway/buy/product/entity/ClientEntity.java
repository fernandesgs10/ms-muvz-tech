package br.com.gateway.buy.product.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "tb_client")
public class ClientEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_seq_client", unique = true, nullable = false)
    private Long coSeqClient;

    @Column(name = "nm_name", nullable = false, length = 80)
    private String nmName;

    @Column(name = "nm_nick_name", length = 80)
    private String nmNickName;

    @Column(name = "dd_birthday", nullable = false, length = 8)
    private LocalDate ddBirthday;

    @OneToMany(mappedBy = "clientEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContactEntity> contacts;

    @OneToMany(mappedBy = "clientEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AddressEntity> address;
}