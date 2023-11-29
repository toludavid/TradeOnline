package com.stanbic.redbox.fincale.online.rest.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RBX_C_OAUTH_2_API_USER")
public class APIUser {
	 @Id
		@Column(name = "ID")
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OAUTH_2_API_USER_seq")
		@SequenceGenerator(name = "OAUTH_2_API_USER_seq", allocationSize = 1, sequenceName = "OAUTH_2_API_USER_seq")
		private Long id;
	private String clientId;
	private String clientSecret;
	private String email;
	private boolean isUserEnabled;
}
