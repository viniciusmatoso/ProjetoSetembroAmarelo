package br.com.projetosetembroamarelo.Controller.Usuario.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import br.com.projetosetembroamarelo.Controller.Usuario.MainUsuarioActivity
import br.com.projetosetembroamarelo.Model.DAO
import br.com.projetosetembroamarelo.Model.Usuario
import br.com.projetosetembroamarelo.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_cadastro.rbMasc
import kotlinx.android.synthetic.main.activity_cadastro.txtEmail
import kotlinx.android.synthetic.main.activity_cadastro.txtIdade
import kotlinx.android.synthetic.main.activity_cadastro.txtNome
import kotlinx.android.synthetic.main.activity_cadastro.txtSenha
import kotlinx.android.synthetic.main.fragment_perfil.*


class PerfilFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)


        var id = 0
        id = activity?.intent?.getIntExtra("id", id)!!

        var usuarios = activity?.let{ DAO.pegarUsuariosParaLogin(it)}

        val nome = view.findViewById<EditText>(R.id.txtNome)
        val idade = view.findViewById<EditText>(R.id.txtIdade)
        val sexo = view.findViewById<RadioGroup>(R.id.rgSexo)
        val masc = view.findViewById<RadioButton>(R.id.rbMasc)
        val fem = view.findViewById<RadioButton>(R.id.rbFem)
        val email = view.findViewById<EditText>(R.id.txtEmail)
        val senha = view.findViewById<EditText>(R.id.txtSenha)

        val btnSalvarUsuario = view.findViewById<Button>(R.id.btSalvarUsuario)

        for(i in usuarios!!){

            if(id.toLong() == i.id){

                nome.setText(i.nome)
                idade.setText(i.idade.toString())
                if(i.sexo == "M"){

                    masc.isChecked = true
                    i.sexo = "M"
                }else{

                    fem.isChecked = true
                }
                email.setText(i.email)
                senha.setText(i.senha)

            }
        }

        (context as MainUsuarioActivity).setarIconToolbar(R.drawable.logo_toolbar)
        (context as MainUsuarioActivity).setarToolbar("")

        btnSalvarUsuario.setOnClickListener {

            var sexo = ""
            if(rbMasc.isChecked){

                sexo = "M"
            }else{

                sexo = "F"
            }
            if(txtNome.text.toString().equals("") || txtIdade.text.toString().equals("") || sexo.equals("") ||
                txtEmail.text.toString().equals("") || txtSenha.text.toString().equals("") ) {

                Toast.makeText(context, "Todos os campos são obrigatórios!", Toast.LENGTH_LONG).show()

            }else {
                val user = Usuario(
                    txtNome.text.toString(),
                    txtIdade.text.toString().toInt(),
                    sexo,
                    txtEmail.text.toString(),
                    txtSenha.text.toString()
                )
                user.id = id.toLong()
                DAO.editUser(user)
                Snackbar.make(
                    layPerfil,
                    "Seus dados foram atualizados com sucesso!",
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }

        return view
    }



}