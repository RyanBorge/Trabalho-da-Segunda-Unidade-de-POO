# Trabalho da Segunda Unidade de POO

Este repositório contém a resolução do trabalho da disciplina de Programação Orientada a Objetos.

## Questão 1 – Sistema de Gestão Hospitalar

**Enunciado:**
Crie uma classe Pessoa contendo: nome; CPF; idade.
Crie uma herança multinível:
• Pessoa
  o Funcionario
    ▪ Medico
    ▪ Enfermeiro

Utilize o comando super para inicializar os atributos das classes pai, nos construtores das classes filhas.
A classe Funcionario deve ser abstrata, contendo:
• método abstrato calcularSalario();
• método concreto exibirDados().

Crie uma interface Plantonista com o método:
• calcularValorPlantao(int horas);
Faça apenas a classe Medico implementar essa interface.

Implemente:
• Sobreescrita do método calcularSalario();
• Sobrecarga do método calcularSalario() recebendo quantidade de plantões extras.

Crie uma exceção personalizada CargaHorariaInvalidaException. Lance essa exceção (throw) quando a carga horária semanal for maior que 60 horas.
Utilize:
• throws na assinatura do método responsável pela validação;
• try-catch na classe principal para tratar a exceção.

Na classe principal:
• crie um menu para cadastrar, remover, alterar e exibir relatórios dos funcionários contendo nome, cargo, salário e carga horária.
• crie um ArrayList<Funcionario>;
• utilize polimorfismo para calcular o salário de todos os funcionários;

---

## Tecnologias Utilizadas
- **Linguagem:** Java
- **Paradigma:** Programação Orientada a Objetos (POO)

## Passo a Passo da Implementação (O que foi feito)

Para solucionar a questão, aplicamos rigorosamente os pilares da Orientação a Objetos no Java. Abaixo, detalhamos cada passo do processo:

1. **Criação da Exceção Personalizada:**
   Criamos a classe `CargaHorariaInvalidaException` que herda de `Exception`. Ela é usada para validar uma regra de negócio, sendo instanciada usando a palavra-chave `super` para passar a mensagem de erro para a superclasse.

2. **Criação da Classe Base `Pessoa`:**
   Criamos a classe `Pessoa` com os atributos protegidos (`protected`) `nome`, `cpf` e `idade`, de modo a serem herdados de forma transparente pelas classes filhas.

3. **Herança e Classe Abstrata `Funcionario`:**
   Criamos a classe abstrata `Funcionario` que estende (`extends`) de `Pessoa`. 
   - No seu construtor, foi utilizado o comando `super(nome, cpf, idade)` para inicializar a parte "Pessoa" do objeto.
   - Definimos o método `exibirDados()` (concreto, com a lógica de impressão).
   - Definimos o método `calcularSalario()` (abstrato), obrigando que classes filhas decidam como calcular seus salários.

4. **Implementação de Interface `Plantonista`:**
   Declaramos a interface `Plantonista` exigindo o comportamento `calcularValorPlantao(int horas)`.

5. **Classes Filhas (`Medico` e `Enfermeiro`) e Polimorfismo:**
   - **`Medico`:** Estende `Funcionario` e implementa (`implements`) `Plantonista`.
     - Utiliza `super()` em seu construtor.
     - Pratica **Sobrescrita (Override)** no método `calcularSalario()` implementando o acréscimo de 20%.
     - Pratica **Sobrecarga (Overload)** ao criar um novo método `calcularSalario(int quantidadePlantoesExtras)`, mantendo o mesmo nome mas com parâmetros diferentes, somando o valor dos plantões.
   - **`Enfermeiro`:** Estende `Funcionario`.
     - Utiliza `super()` no construtor.
     - Pratica **Sobrescrita (Override)** adicionando o bônus fixo de R$ 500 no salário.

6. **Classe Principal (Menu e Coleções):**
   - Utilizamos um `ArrayList<Funcionario>` para armazenar tanto instâncias de `Medico` quanto de `Enfermeiro`, aproveitando o **Polimorfismo**.
   - Criamos um Menu Interativo de Console com a classe `Scanner`.
   - Adicionamos a opção de "Alterar", "Remover", "Cadastrar" e "Exibir".
   - Criamos um método `validarCargaHoraria(int horas) throws CargaHorariaInvalidaException` para lançar o erro se `horas > 60`.
   - Utilizamos o bloco estrutural `try-catch` nos momentos em que dados são inseridos ou editados, blindando o programa contra falhas bruscas (crashes) e exibindo mensagens de erro de negócio.
   - Na opção "Exibir Relatórios", iteramos a lista (`for (Funcionario f : funcionarios)`) executando `f.exibirDados()` e o Java, por meio do **Polimorfismo Dinâmico**, direciona a chamada para o método correto dependendo da classe instanciada (Médico ou Enfermeiro).

Todo o código-fonte foi vastamente documentado e comentado com o intuito de detalhar o papel de cada conceito, permitindo um rápido entendimento para avaliação.