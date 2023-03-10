<?php

namespace App\Form;

use App\Entity\Reclamation;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;

class ReclamationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom',null,[
            'attr' => ['requied' => false, 
                       'placeholder' => 'Entrer Votre Nom '] ] ) 
            ->add('prenom',null,[
                'attr' => ['requied' => false, 
                           'placeholder' => 'Entrer Votre Prenom '] ])
            ->add('email',null,[
                'attr' => ['requied' => false, 
                           'placeholder' => 'Entrer Votre Email '] ])
            ->add('description',)
            //->add('date_rec')
          //  ->add('type_rec')
          ->add('type_rec',ChoiceType::class,['choices'=> [
           // ''=>'',
            'Normale'=>'1',
            'Administrative'=>'2',
            
        ],])

        ;
    }

    

    
      
    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Reclamation::class,
        ]);
    }
}
