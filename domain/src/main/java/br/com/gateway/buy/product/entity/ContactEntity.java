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
@Table(name = "tb_contact")
public class ContactEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_seq_contact", unique = true, nullable = false)
    private Long coSeqContact;

    @Column(name = "nm_telephone", length = 15)
    private String nmTelephone;

    @Column(name = "nm_cellphone", nullable = false, length = 15)
    private String nmCellphone;

    @Column(name = "nm_email", nullable = false, length = 30)
    private String nmEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "co_seq_client")
    @JsonBackReference
    @ToString.Exclude
    private ClientEntity clientEntity;

}