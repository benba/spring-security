/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.saml2.provider.service.authentication;

import org.joda.time.DateTime;
import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleSaml2AuthenticatedPrincipalTests {

	@Test
	public void createSimpleSaml2AuthenticatedPrincipal() {
		Map<String, List<Object>> attributes = new LinkedHashMap<>();
		attributes.put("email", Arrays.asList("john.doe@example.com", "doe.john@example.com"));
		SimpleSaml2AuthenticatedPrincipal principal = new SimpleSaml2AuthenticatedPrincipal("user", attributes);
		assertThat(principal.getName()).isEqualTo("user");
		assertThat(principal.getAttributes()).isEqualTo(attributes);
	}

	@Test
	public void getFirstAttributeWhenStringValueThenReturnsValue() {
		Map<String, List<Object>> attributes = new LinkedHashMap<>();
		attributes.put("email", Arrays.asList("john.doe@example.com", "doe.john@example.com"));
		SimpleSaml2AuthenticatedPrincipal principal = new SimpleSaml2AuthenticatedPrincipal("user", attributes);
		assertThat(principal.<String>getFirstAttribute("email")).isEqualTo(attributes.get("email").get(0));
	}

	@Test
	public void getAttributeWhenStringValuesThenReturnsValues() {
		Map<String, List<Object>> attributes = new LinkedHashMap<>();
		attributes.put("email", Arrays.asList("john.doe@example.com", "doe.john@example.com"));
		SimpleSaml2AuthenticatedPrincipal principal = new SimpleSaml2AuthenticatedPrincipal("user", attributes);
		assertThat(principal.<String>getAttribute("email")).isEqualTo(attributes.get("email"));
	}

	@Test
	public void getAttributeWhenDistinctValuesThenReturnsValues() {
		final Boolean registered = true;
		final Instant registeredDate = Instant.ofEpochMilli(DateTime.parse("1970-01-01T00:00:00Z").getMillis());

		Map<String, List<Object>> attributes = new LinkedHashMap<>();
		attributes.put("registration", Arrays.asList(registered, registeredDate));

		SimpleSaml2AuthenticatedPrincipal principal = new SimpleSaml2AuthenticatedPrincipal("user", attributes);

		List<Object> registrationInfo = principal.getAttribute("registration");

		assertThat(registrationInfo).isNotNull();
		assertThat((Boolean) registrationInfo.get(0)).isEqualTo(registered);
		assertThat((Instant) registrationInfo.get(1)).isEqualTo(registeredDate);
	}
}
