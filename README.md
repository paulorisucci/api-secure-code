# API Secure Code

A API Secure Code é uma API Rest feita para fornecer soluções de segurança, como a validação de contratos, criptografia
de valores ou objetos inteiros, backup de diretórios e autenticação de usuários

## Endpoints disponíveis:

### Criação de contrato: 
* POST /contract/{id}

### Validação de objetos através do contrato criado: 
* POST /contract/{id}/validate

### Criptografar os valores de objetos JSON:
* POST /cryptography/sync/encrypt-values

### Descriptografar os valores de objetos JSON:
* POST /cryptography/sync/decrypt-values

## Como usar?

### Criação de contrato:
```
Envie uma requisição POST para o endpoint /contract/{id} com o nome do contrato que deseja criar no lugar
do {id} do path, e o body da requisição contendo um objeto JSON que possua os nomes dos atributos do
contrato como chave, e seus respectivos tipos como valor

Exemplo: Deseja-se validar a tipagem dos dados cadastrais de uma pessoa. Sabe-se que a pessoa possui um
nome (string), CPF (string) e uma flagNecessidadeEspecial (boolean) para saber se a mesma é portadora
de deficiência.

Então, enviariamos uma requisição para criar um contrato "pessoa", que iria conter os atributos nome, cpf
e flagNecessidadeEspecial. A requisição seria da seguinte forma:

POST {URL}:{PORT}/contract/pessoa

BODY:
{
"nome": "string",
"cpf": "string"
"flagNecessidadeEspecial": "boolean"
}

desta forma, o contrato será cadastrado na base de dados, e poderá ser usado no endpoint de validação de
objetos para validar os dados de uma Pessoa 
```

### Validação de objetos através do contrato criado:
```
Envie uma requisição POST para o endpoint /contract/{id}/validate com o nome do contrato já cadastrado 
que deseja utilizar na validação dos objetos no lugar do {id} do path, e o body da requisição contendo um
objeto ou lista JSON que possua os objetos a serem validados.

Exemplo: Deseja-se validar a tipagem dos dados cadastrais de uma pessoa. Sabe-se que a pessoa possui um
nome (string), CPF (string) e uma flagNecessidadeEspecial (boolean) para saber se a mesma é portadora
de deficiência.

Então, enviariamos uma requisição para validar os objetos com o contrato criado anteriormente, "pessoa".
A requisição seria da seguinte forma:

POST {URL}:{PORT}/contract/pessoa/validate

BODY:

{
  "nome": "João",
  "cpf": "12345678910"
  "flagNecessidadeEspecial": false
}

Ou, contendo uma lista de objetos a serem validados:   

[
  {
    "nome": "João",
    "cpf": "12345678910"
    "flagNecessidadeEspecial": false
  },
  {
    "nome": "Maria",
    "cpf": "12345678911"
    "flagNecessidadeEspecial": true
  }
]

desta forma, o contrato contendo a tipagem correta dos atributos será recuperado e utilizado para validar
cada um dos objetos presentes na requisição. Caso algum dos atributos possua o tipo errado, ou não esteja
presente no contrato, o mesmo retorna um erro 400.
```

### Criptografar os valores de objetos JSON:
```
Envie uma requisição POST para o endpoint /cryptography/sync/encrypt-values com o body da requisição contendo um
objeto ou lista JSON a ter seus valores criptografados

Exemplo: Deseja-se criptografar os dados cadastrais de uma pessoa. Sabe-se que seus dados cadastrais são um email
e uma senha.

Então, enviariamos uma requisição para criptografar esses dois valores.

A requisição seria da seguinte forma:

POST {URL}:{PORT}/cryptography/sync/encrypt-values

BODY:

{
  "email": "joao123@email.com",
  "senha": "IVJANdvjsl88&*(&"
}

Ou, contendo uma lista de objetos a serem criptografados:   

[
  {
    "email": "joao123@email.com",
    "senha": "IVJANdvjsl88&*(&"
  },
  {
    "email": "maria@email.com",
    "senha": ")(*VEWU1JK#$c32"
  }
]

desta forma, cada valor do body será criptografado individualmente, preservando suas chaves originais e modificando apenas
os valores a serem protegidos.

Ao utilizar esse endpoint, deve-se enviar requisições apenas com valores textuais.

Exemplo de resposta:

{
    "senha": "yxVVo09+id4MK4UndUPg9A==",
    "email": "fE82nOivKoJpanbDk9355g=="
}
```

### Descriptografar os valores de objetos JSON:
```
Envie uma requisição POST para o endpoint /cryptography/sync/decrypt-values com o body da requisição contendo um
objeto ou lista JSON a ter seus valores descriptografados.

Os valores precisam ter sido criptografados anteriormente pelo endpoint /cryptography/sync/encrypt-values.

Exemplo: Deseja-se descriptografar os dados cadastrais de uma pessoa. Sabe-se que seus dados cadastrais são um email
e uma senha.

Então, enviariamos uma requisição para descriptografar esses dois valores.

A requisição seria da seguinte forma:

POST {URL}:{PORT}/cryptography/sync/decrypt-values

BODY:

{
    "email": "fE82nOivKoJpanbDk9355g==",
    "senha": "yxVVo09+id4MK4UndUPg9A=="
}

Ou, contendo uma lista de objetos a serem descriptografados:   

[
    {
        "senha": "7x3a5M0Ki94FwbBVd+p0HSKbYqdPKlZ4AzvpRpcH4pM=",
        "email": "mxZcAj1rzVhQvuM+QelwUfORI15CPARiylTC6HN3bW0="
    },
    {
        "senha": "yxVVo09+id4MK4UndUPg9A==",
        "email": "lUREhdWTvKcWUbR2EA3pXkNI6vChZmqi486X20B4JN8="
    }
]

desta forma, cada valor do body será descriptografado individualmente, preservando suas chaves originais e modificando apenas
os valores a serem visualizados.

Ao utilizar esse endpoint, deve-se enviar requisições apenas com valores textuais.

Exemplo de resposta:

{
    "senha": ")(*VEWU1JK#$c32",
    "email": "maria123@email.com"
}
```