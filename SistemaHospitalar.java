import java.util.ArrayList;
import java.util.Scanner;

// 1. Exceção Personalizada
// A classe estende 'Exception' para que possamos lançar e tratar nossa própria regra de negócio
class CargaHorariaInvalidaException extends Exception {
    public CargaHorariaInvalidaException(String mensagem) {
        // O comando super chama o construtor da classe pai (Exception) passando a mensagem
        super(mensagem);
    }
}

// 2. Classe Base (Pai) - Pessoa
// Centraliza os atributos comuns a qualquer pessoa no sistema
class Pessoa {
    // Atributos protegidos (protected) permitem o acesso direto pelas classes filhas
    protected String nome;
    protected String cpf;
    protected int idade;

    // Construtor da classe Pessoa para inicializar os atributos
    public Pessoa(String nome, String cpf, int idade) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }

    // Métodos Getters e Setters
    public String getCpf() { return cpf; }
    public void setNome(String nome) { this.nome = nome; }
    public void setIdade(int idade) { this.idade = idade; }
}

// 3. Classe Abstrata Funcionario (Herança de Pessoa)
// Abstract: Não pode ser instanciada diretamente, serve apenas como molde para outras classes
abstract class Funcionario extends Pessoa {
    protected double salarioBase;
    protected int cargaHorariaSemanal;

    public Funcionario(String nome, String cpf, int idade, double salarioBase, int cargaHorariaSemanal) {
        // Comando 'super' chama o construtor da classe pai (Pessoa) para reutilizar a inicialização
        super(nome, cpf, idade); 
        this.salarioBase = salarioBase;
        this.cargaHorariaSemanal = cargaHorariaSemanal;
    }

    // Método abstrato: Obriga todas as classes filhas a implementarem sua própria forma de calcular o salário
    public abstract double calcularSalario();

    // Método concreto: Já possui implementação e pode ser usado (ou sobrescrito) pelas classes filhas
    public void exibirDados() {
        System.out.println("Nome: " + nome + " | CPF: " + cpf + " | Idade: " + idade);
        System.out.println("Carga Horária: " + cargaHorariaSemanal + "h | Salário: R$ " + String.format("%.2f", calcularSalario()));
    }

    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }
    public void setCargaHorariaSemanal(int cargaHorariaSemanal) { this.cargaHorariaSemanal = cargaHorariaSemanal; }
}

// 4. Interface Plantonista
// Define um contrato. Qualquer classe que implementar essa interface, deverá ter esse método.
interface Plantonista {
    double calcularValorPlantao(int horas);
}

// 5. Classe Medico (Herda de Funcionario e Implementa a interface Plantonista)
class Medico extends Funcionario implements Plantonista {
    private String crm; // Atributo exclusivo de Medico

    public Medico(String nome, String cpf, int idade, double salarioBase, int cargaHorariaSemanal, String crm) {
        // O comando 'super' inicializa os atributos de 'Funcionario' e 'Pessoa'
        super(nome, cpf, idade, salarioBase, cargaHorariaSemanal);
        this.crm = crm;
    }
    
    public void setCrm(String crm) { this.crm = crm; }

    // Polimorfismo - Sobrescrita (Override)
    // O método calcularSalario foi definido abstrato na classe pai (Funcionario). Aqui dizemos como o médico calcula.
    @Override
    public double calcularSalario() {
        return salarioBase + (salarioBase * 0.20); // Médico ganha 20% de adicional
    }

    // Polimorfismo - Sobrecarga (Overload)
    // Mesma assinatura de método (calcularSalario), porém com parâmetros diferentes.
    public double calcularSalario(int quantidadePlantoesExtras) {
        double valorExtra = calcularValorPlantao(12) * quantidadePlantoesExtras; // Plantão hipotético de 12h
        return calcularSalario() + valorExtra; // Chama o calcularSalario() acima e adiciona o extra
    }

    // Implementação obrigatória vinda da interface Plantonista
    @Override
    public double calcularValorPlantao(int horas) {
        return horas * 150.0; // R$ 150 por hora de plantão
    }

    // Polimorfismo - Sobrescrita (Override) do método exibirDados
    @Override
    public void exibirDados() {
        System.out.println("--- [ MÉDICO ] ---");
        super.exibirDados(); // Exibe os dados gerais (Nome, cpf, salário, etc) da classe pai
        System.out.println("CRM: " + crm); // E adiciona a informação exclusiva do Médico
    }
}

// 6. Classe Enfermeiro (Herda de Funcionario)
class Enfermeiro extends Funcionario {
    private String coren; // Atributo exclusivo de Enfermeiro

    public Enfermeiro(String nome, String cpf, int idade, double salarioBase, int cargaHorariaSemanal, String coren) {
        // Comando 'super' para inicializar atributos da superclasse
        super(nome, cpf, idade, salarioBase, cargaHorariaSemanal);
        this.coren = coren;
    }
    
    public void setCoren(String coren) { this.coren = coren; }

    // Polimorfismo - Sobrescrita (Override)
    // Implementação da regra de negócio salarial exclusiva para Enfermeiro
    @Override
    public double calcularSalario() {
        return salarioBase + 500.0; // Enfermeiro ganha bônus fixo de R$ 500
    }

    // Polimorfismo - Sobrescrita (Override)
    @Override
    public void exibirDados() {
        System.out.println("--- [ ENFERMEIRO ] ---");
        super.exibirDados();
        System.out.println("COREN: " + coren);
    }
}

// 7. Classe Principal (com método main e Menu iterativo)
public class SistemaHospitalar {
    // Uso de Collection (ArrayList) para armazenar os cadastros de forma dinâmica
    private static ArrayList<Funcionario> funcionarios = new ArrayList<>();
    // Uso do Scanner para coletar dados do teclado (Console)
    private static Scanner scanner = new Scanner(System.in);

    // O comando "throws" indica que esse método é perigoso e PODE lançar uma CargaHorariaInvalidaException.
    // Quem chamar este método (no caso, cadastrar/alterar) deverá obrigatoriamente tratar (try/catch).
    public static void validarCargaHoraria(int horas) throws CargaHorariaInvalidaException {
        if (horas > 60) {
            // O comando 'throw' efetivamente "lança" o erro com a mensagem especificada.
            throw new CargaHorariaInvalidaException("Erro: A carga horária não pode exceder 60 horas semanais!");
        }
    }

    public static void main(String[] args) {
        int opcao = -1;
        // Loop 'do-while' para exibir o menu até o usuário escolher sair (0)
        do {
            System.out.println("\n=== SISTEMA DE GESTÃO HOSPITALAR ===");
            System.out.println("1. Cadastrar Médico");
            System.out.println("2. Cadastrar Enfermeiro");
            System.out.println("3. Remover Funcionário");
            System.out.println("4. Alterar Funcionário");
            System.out.println("5. Exibir Relatórios (Polimorfismo)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            try {
                // Captura a opção informada pelo usuário
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // Previne crash caso o usuário digite letra onde se espera um número
                opcao = -1;
            }

            // Switch case para direcionar a execução
            switch (opcao) {
                case 1: cadastrarFuncionario(true); break;
                case 2: cadastrarFuncionario(false); break;
                case 3: removerFuncionario(); break;
                case 4: alterarFuncionario(); break;
                case 5: exibirRelatorios(); break;
                case 0: System.out.println("Saindo..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // Método para cadastrar. A flag isMedico diferencia entre as duas classes
    private static void cadastrarFuncionario(boolean isMedico) {
        // Bloco try-catch para tratar exceções ao cadastrar (incluindo nossa Exceção Personalizada)
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            
            System.out.print("Idade: ");
            int idade = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Salário Base: ");
            double salario = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Carga Horária Semanal: ");
            int cargaHoraria = Integer.parseInt(scanner.nextLine());

            // Chama o método que tem "throws". Se a carga for maior que 60, pula direto para o 'catch' abaixo
            validarCargaHoraria(cargaHoraria);

            // Condicional para decidir que tipo de objeto será instanciado
            if (isMedico) {
                System.out.print("CRM: ");
                String crm = scanner.nextLine();
                // Instancia um Medico e joga na lista de Funcionario (Graças ao polimorfismo/herança)
                funcionarios.add(new Medico(nome, cpf, idade, salario, cargaHoraria, crm));
                System.out.println("Médico cadastrado com sucesso!");
            } else {
                System.out.print("COREN: ");
                String coren = scanner.nextLine();
                // Instancia um Enfermeiro e joga na lista de Funcionario
                funcionarios.add(new Enfermeiro(nome, cpf, idade, salario, cargaHoraria, coren));
                System.out.println("Enfermeiro cadastrado com sucesso!");
            }
            
        } catch (CargaHorariaInvalidaException e) {
            // Tratamento Específico da nossa exceção de negócio
            System.out.println(e.getMessage()); 
        } catch (Exception e) {
            // Tratamento genérico, caso digitem textos onde era para ser número (Input mismatch)
            System.out.println("Erro na entrada de dados. Tente novamente com valores corretos.");
        }
    }

    // Remove funcionário buscando pelo CPF usando Lambda e removeIf do ArrayList
    private static void removerFuncionario() {
        System.out.print("Digite o CPF do funcionário a ser removido: ");
        String cpf = scanner.nextLine();
        
        // Verifica se o elemento tem o cpf igual ao digitado e o remove da lista
        boolean removido = funcionarios.removeIf(f -> f.getCpf().equals(cpf));
        
        if (removido) {
            System.out.println("Funcionário removido com sucesso.");
        } else {
            System.out.println("Funcionário não encontrado pelo CPF informado.");
        }
    }

    // Permite buscar um funcionário e modificar seus atributos
    private static void alterarFuncionario() {
        System.out.print("Digite o CPF do funcionário a ser alterado: ");
        String cpf = scanner.nextLine();
        
        Funcionario func = null; // Variavel auxiliar para encontrar o funcionário alvo
        for (Funcionario f : funcionarios) {
            if (f.getCpf().equals(cpf)) {
                func = f; // Guarda o objeto encontrado
                break;
            }
        }

        if (func == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        // Try catch pois também iremos validar a carga horária em caso de mudança
        try {
            System.out.print("Novo Nome (" + func.nome + ") [deixe vazio para manter]: ");
            String nome = scanner.nextLine();
            if (!nome.trim().isEmpty()) func.setNome(nome);

            System.out.print("Nova Idade (" + func.idade + ") [deixe vazio para manter]: ");
            String idadeStr = scanner.nextLine();
            if (!idadeStr.trim().isEmpty()) func.setIdade(Integer.parseInt(idadeStr));

            System.out.print("Novo Salário Base (" + func.salarioBase + ") [deixe vazio para manter]: ");
            String salarioStr = scanner.nextLine();
            if (!salarioStr.trim().isEmpty()) func.setSalarioBase(Double.parseDouble(salarioStr));

            System.out.print("Nova Carga Horária Semanal (" + func.cargaHorariaSemanal + ") [deixe vazio para manter]: ");
            String cargaStr = scanner.nextLine();
            if (!cargaStr.trim().isEmpty()) {
                int cargaHoraria = Integer.parseInt(cargaStr);
                // Testa se a nova carga horária é válida
                validarCargaHoraria(cargaHoraria);
                func.setCargaHorariaSemanal(cargaHoraria);
            }

            // Uso do 'instanceof' para descobrir dinamicamente se a classe do Funcionario é Médico ou Enfermeiro
            if (func instanceof Medico) {
                System.out.print("Novo CRM [deixe vazio para manter]: ");
                String crm = scanner.nextLine();
                // O (Medico) faz um cast, tratando a variável "func" momentaneamente como Médico para usar o SetCrm
                if (!crm.trim().isEmpty()) ((Medico) func).setCrm(crm);
            } else if (func instanceof Enfermeiro) {
                System.out.print("Novo COREN [deixe vazio para manter]: ");
                String coren = scanner.nextLine();
                if (!coren.trim().isEmpty()) ((Enfermeiro) func).setCoren(coren);
            }
            
            System.out.println("Funcionário alterado com sucesso!");

        } catch (CargaHorariaInvalidaException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro na entrada de dados. Tente novamente.");
        }
    }

    // Exibe os relatórios aplicando o conceito de Polimorfismo na prática
    private static void exibirRelatorios() {
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }
        System.out.println("\n=== RELATÓRIO DE FUNCIONÁRIOS ===");
        
        // Uso de Polimorfismo: O laço itera sobre 'Funcionario', 
        // A máquina virtual Java descobre sozinha, em tempo de execução, se o objeto 'f' 
        // é Medico ou Enfermeiro e chama o método exibirDados() e calcularSalario() correspondente.
        for (Funcionario f : funcionarios) {
            f.exibirDados();
            System.out.println("--------------------------------");
        }
    }
}
