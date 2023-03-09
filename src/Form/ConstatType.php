<?php

namespace App\Form;

use App\Entity\Constat;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Validator\Constraints as Assert;
 use Symfony\Component\Validator\Constraints\Range;
 use Symfony\Component\Validator\Constraints\NotBlank;
use App\Entity\Expert;
class ConstatType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
          
        
            ->add('id_client',null,  [
                'constraints' => [
                    new Range([
                        'min' => 1,
                        'minMessage' => "L'ID doit être supérieur à {{ limit }}."
                    ]),
                    new NotBlank([
                        'message' => 'Veuillez saisir votre ID'
                    ])
                ],
            ])
            ->add('id_vehicule')
            ->add('date')
            ->add('lieu' , ChoiceType::class, [
                'choices' => [
                    'tunis ' => 'tunis',
                    'gabes ' => 'gabes',
                   
                ],
            ])
            ->add('image', FileType::class, [
                'label' => 'Image (JPG, PNG, GIF)',
                'required' => false,
                'mapped' => false

            ])
            ->add('Expert',EntityType::class,
            ['class'=>Expert::class,
            'choice_label'=>'id','multiple'=>false])
            
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Constat::class,
        ]);
    }
}
