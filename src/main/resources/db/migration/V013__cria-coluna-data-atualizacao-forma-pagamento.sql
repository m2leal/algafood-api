alter table forma_pagamento add data_atualizacao datetime null;
update forma_pagamento set data_atualizacao = CURRENT_TIMESTAMP;
alter table forma_pagamento modify data_atualizacao datetime not null;